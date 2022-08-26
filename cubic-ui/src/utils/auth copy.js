import Cookies from 'js-cookie'

const TokenKey = 'SSO_AS_SUB_TICKET_SCSP'

export function getToken() {
  return Cookies.get(TokenKey)
}

export function setToken(token) {
  return Cookies.set(TokenKey, token)
}

export function removeToken() {
  return Cookies.remove(TokenKey)
}
