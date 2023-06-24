-- Selon la documentation : http://www.h2gis.org/docs/1.5.0/quickstart/
-- "To load the spatial functions, the user must apply the SQL syntax:"
CREATE ALIAS IF NOT EXISTS H2GIS_SPATIAL FOR "org.h2gis.functions.factory.H2GISFunctions.load";
CALL H2GIS_SPATIAL();