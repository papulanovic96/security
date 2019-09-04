export interface LoginResponse {
  username: string;
  accessToken: string;
  grantedAuthorities: string[];
}

export class SigninRequest {
  username: string;
  password: string;
}
