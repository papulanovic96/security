import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Certificate } from './certificate';

@Injectable({
  providedIn: 'root'
})
export class CertificateService {


  private getURL: string;
  private createURL: string;
  private deleteURL: string;

  constructor(private http: HttpClient) { 
    this.getURL = 'http://localhost:4200/certificate/findAll';
  }

  findAll() : Observable<Certificate[]> {
    return this.http.get<Certificate[]>(this.getURL);
  }
}
