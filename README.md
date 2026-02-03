Proyecto StarWars DB (API + Hibernate + MongoDB)

Este proyecto permite trabajar con información del universo Star Wars obtenida desde una API externa y almacenarla en diferentes 
sistemas de persistencia (base de datos relacional mediante Hibernate/JPA y base de datos NoSQL mediante MongoDB).

⚠️ IMPORTANTE (ANTES DE EJECUTAR)

Antes de ejecutar el proyecto es obligatorio configurar correctamente las credenciales de la base de datos:

1️⃣ Modificar properties.xml

Debes cambiar el usuario y la contraseña para acceder a tu base de datos.

Ruta:
src\main\resources\META-INF/properties.xml

Asegúrate de introducir tu usuario y contraseña reales.

2️⃣ Modificar la clase DBConnector

Además del archivo properties.xml, también es necesario revisar la clase DBConnector, ya que ahí se define la conexión a la base de datos.

Cambia el usuario y la contraseña en la configuración de conexión para que coincidan con los datos de tu sistema.
