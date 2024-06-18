const authorizationCode = () => {
  window.location = new URL('http://localhost:8081/oauth2/authorization/keycloak');
}
