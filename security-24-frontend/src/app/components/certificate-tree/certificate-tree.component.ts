import { Component, OnInit } from '@angular/core';
import {CertRequestImpl} from '../../models/Impl/cert-request-impl';
import {CertificateService} from '../../services/certificate.service';
import {TreeItem} from '../../models/treeItem';
import {ToastrService} from 'ngx-toastr';
import { saveAs } from 'file-saver';

@Component({
  selector: 'app-certificate-tree',
  templateUrl: './certificate-tree.component.html',
  styleUrls: ['./certificate-tree.component.css']
})
export class CertificateTreeComponent implements OnInit {
  private nodes: TreeItem[];
  private options: any;

  private data = new CertRequestImpl();
  public showSpinner: boolean;

  /* subject data */
  private countryNameC: string;
  private organizationO: string;
  private organizationalUnitOU: string;
  private commonNameCN: string;
  private typeCert: string;

  private selectedNodeId: number;
  private selectedNodeName: string;

  cert: TreeItem;

  constructor(private certService: CertificateService,
              private toastrService: ToastrService) {
    this.nodes = null;
  }

  ngOnInit() {
    this.nodes = [];
    this.options = {};

    this.fetchForest();
  }

  onTreeNodeSelect(id: number, name: string) {
    this.selectedNodeId = id;
    this.selectedNodeName = name;
    this.data.issuer = this.selectedNodeName;
  }

  fetchForest() {
    this.certService
      .getForest()
      .subscribe( 
        response => { this.nodes = response },
        (err) => {
          this.toastrService.error(err.error.apierror.message);
        }
      );
  }

  create() {
    if (this.data.issuer === undefined && this.data.certificateType !== 'ROOT') {
      this.toastrService.error('Only ROOT cert can be created with no issuer data.');
      return;
    }

    this.showSpinner = true;
    this.data.subject =
        'C=' + this.countryNameC + ', ' +
        'O=' + this.organizationO + ', ' +
        'OU=' + this.organizationalUnitOU + ', ' +
        'CN=' + this.commonNameCN;

    this.certService
      .create(this.data)
      .subscribe(
        () => {
          this.fetchForest();
          this.toastrService.success('Certificate created.');
          this.showSpinner = false;
        },
        (err) => {
          this.toastrService.error(err.error.apierror.message);
          this.showSpinner = false;
        }
      );
  }

  startDownload() {
    if (this.selectedNodeId === undefined) {
      this.toastrService.error('Please select certificate to download.');
      return;
    }

    this.certService
      .downloadZip(this.selectedNodeId)
      .then(
        blob => { saveAs(blob, 'data.zip' );
    });
  }

}
