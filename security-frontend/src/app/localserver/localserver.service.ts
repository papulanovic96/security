import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { LocalServer } from './localserver';
import { HttpClient, HttpHeaders } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class LocalserverService {

  private getURL : string;
  private postURL: string;
  private deleteURL: string;

  constructor(private http: HttpClient) {
    this.getURL = 'http://localhost:4200/localserver/findAll';
    this.postURL = 'http://localhost:4200/localserver/save';
    this.deleteURL = 'http://localhost:4200/localserver/delete';
   }

   findAll() : Observable<LocalServer[]>{
    return this.http.get<LocalServer[]>(this.getURL);
   }

   createNew(newServer: LocalServer) : Observable<LocalServer>{
    return this.http.post<LocalServer>(this.postURL, newServer, {
      headers: new HttpHeaders().set('Content-Type', 'application/json'),
      responseType: 'text' as 'json'
   });
   }

   deleteOld(serverId: number) : Observable<Object>{
     return this.http.delete(this.deleteURL + '/' + serverId, {
      headers: new HttpHeaders().set('Content-Type', 'application/json'),
      responseType: 'text' as 'json'
   });
   }
}
