import { Component, OnInit } from '@angular/core';
import { CertificateService } from './certificate.service';
import { Certificate } from './certificate';

@Component({
  selector: 'app-certificate',
  templateUrl: './certificate.component.html',
  styleUrls: ['./certificate.component.css']
})
export class CertificateComponent implements OnInit {

  certificateList: Certificate[];

  constructor(private certificateService: CertificateService) { }

  ngOnInit() {
    this.certificateService.findAll().subscribe(
      certificateList => this.certificateList = certificateList
    )
  }

}
