import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SigninRequest, LoginResponse } from '../models/login-response';

const httpOptions = {
  headers: new HttpHeaders({ 'Content-Type': 'application/json' })
};

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  private signinUrl = 'https://localhost:8443/login-service/';

  constructor(private http: HttpClient) {
  }

  signIn(credentials: SigninRequest): Observable<LoginResponse> {
    return this.http.post<LoginResponse>(this.signinUrl, credentials, httpOptions);
  }

}
