# park-king
Marre de tourner en rond à la recherche de places ? :car: :parking:  

Cette API REST regroupe plusieurs sources données de différentes villes, afin de faciliter la recherche d'un parking.

L'API est modulaire : l'intégration de nouvelles villes se fait par l'ajout de `connecteurs`.

Une fois le projet lancé, la documentation peut être consultée à l'adresse suivante : http://localhost:8080/swagger-ui.html

## Objectifs


- Lister les parking d'un maximum de villes, les parkings à proximité d'un point (ex : position de l'utilisateur)
- Retourner le nb de places, et les places libres en "temps réel" (si disponible) d'un parking
- Pour chaque ville, exposer les données des parking sous un format de réponse standard et commun : plus facile à intégrer pour un client
- Developper-friendly : faciliter la maintenance, et l'ajout de nouvelles villes via des `connecteurs`


## Structure du projet

### Modèle de données

Le projet est simple. Il y a une seule table `City` qui recense les différentes villes intégrées dans l'API. 
En effet, pour des raisons de performance, et de navigation, nous ne pouvons pas afficher les données parking de toutes les villes à la fois.
Quelque soit le client (application mobile, site web), celui-ci devra demander à l'utilisateur de choisir une ville ou de se baser sur la position actuelle de l'utilisateur pour laisser l'API déterminer la ville.

Table `City`


| Colonne       | Type      |
| --------------|---------- |
| code (PK)     | varchar   |
| label         | varchar   |
| polygonArea   | polygon   |
| isDisabled    | bit       |
| connector     | varchar   |

Je me suis inspiré de [CityMapper](https://citymapper.com/) pour compartimenter les données par ville.

Colonnes : 
- label : le libellé de la ville
- polygonArea : permet de déterminer via un polygone la zone couverte par la ville. Cela permet notamment à un client de demander la ville correspondant à une position géographique (ex: sa position actuelle)
- isDisabled : permet de ne plus faire remonter une ville, pratique en cas de maintenance ou d'indisponibilité des données
- connector : le nom du `connecteur` à utiliser

Un exemple de paramétrage en base de données :

| code  | label | polygonArea | isDisabled | connector |
| ----- | ----- | ----------- |-----|------------------|
| poitiers | Poitiers | POLYGON ((0.29368210308794573 46.56978644438371, 0.3065647832412708 46.55731866234689, 0.3331188927708979 46.55560681994356, 0.3629922659933129 46.56321459505472, 0.3723968464515508 46.5818491359758, 0.354970712072855 46.60408811112782, 0.31460849231839916 46.60186362267095, 0.29466816331336076 46.5840955120762, 0.29368210308794573 46.56978644438371)) | 0 | poitiers-connector |

### Modules

Le projet est composé sous forme de modules Maven. Cette architecture n-tiers a été choisie pour faciliter la maintenance et l'expérience développeur en déléguant à chaque module une responsabilité particulière. Cela permet également de mettre en place des tests par module (intégration pour la couche webservice, JUnit pour la couche service).


```
|
|_parkking-api                  <= Module principal de l'API Parkking
|   |_parkking-webservice       <= Couche web (controllers)
|   |_parkking-db               <= Couche persistence
|   |_parkking-service          <= Couche business, qui utilise les connecteurs
|
|_parkking-cities-connector     <= Module pour les connecteurs de villes
   |_cities-common-connector    <= Interface, dto, utilitaires communs
   |_[city-A]-connector
   |_[city-B]-connector
   ...
```

**Notes :**
- Le module API :
   - Reçoit la requête du client. Détermine quel `connecteur` utiliser, l'utilise pour récupérer les parking de la ville selon la requête, retourne les données standardisée au client. C'est l'interface entre le client et les connecteur
   - Est divisée en n-tiers couches : webservice, service, db
- L'API utilise ce qu'on appelle ici des `connecteurs` pour récupérer les données parking des différentes villes
- La couche API et les connecteurs sont 2 modules à part. En effet, l'API utilise les connecteurs comme source de données. On peut considérer que les connecteurs forment une librairie interne au projet


#### Connecteurs
Chaque ville dans l'API fait l'objet d'un connecteur. Un connecteur est chargé de récupérer les données parking d'une ville depuis une ou plusieurs sources de données, et de les exposer dans une réponse standardisée. Le module API utilise ensuite ces données pour les exposer au client.


L'idée est de proposer une structure modulaire :
- Les connecteurs implémentent un contrat d'interface commun : ils prennent en paramètres les même input, et retournent en sortie les mêmes output
- Les modules peuvent être ajoutés, désactivés (utilisation de profiles sous Maven), supprimés facilement. Cela permet d'ajouter des villes plus facilement, ou bien de décommissionner / migrer également une ville sans impacter les autres villes
- 1 ville = 1 connecteur : facilite la maintenance, on sait où naviguer pour maintenir un composant


### Ajouter une nouvelle ville




## Choix techniques


Pourquoi cette structure et ces choix ?


Modèle initial générique : utiliser XPATH et paramétrer les expressions en db.


Ce que j'aurai mis en place dans un contexte pro :
- JUnit, Tests d'intégration pour le module webservice et les connecteurs
- Profile pour chaque module de connecteur, permettant de les désactiver à la demande sans avoir à commenter le code