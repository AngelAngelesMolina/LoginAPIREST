
# LoginAPIREST

Este es un proyecto de kotlin para consumir una API permitiendo registrar e ingresar a un usuario, además de poder ver los administradores. 
 




## Tecnologías utilizadas
- Kotlin
- Volley 
- GSON 
  




## API Reference
- [Link](https://reqres.in/)

#### Obtener todos los usuarios

```http
  GET - https://reqres.in/api/users?page=2
```


#### Registrar user

```http
  POST- https://reqres.in/api/register
```

| Parameter | Type     | Description                       |
| :-------- | :------- | :-------------------------------- |
| `email`      | `string` | **Required**. Correo del usuario |
| `password`      | `string` | **Required**. Contraseña del usuario|

 Regresa el id y el token del usuario

 #### Ingresar

```http
  POST- https://reqres.in/api/register
```

| Parameter | Type     | Description                       |
| :-------- | :------- | :-------------------------------- |
| `email`      | `string` | **Required**. Correo del usuario |
| `password`      | `string` | **Required**. Contraseña del usuario |

 Regresa el token del usuario.

