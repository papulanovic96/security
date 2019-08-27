import { Component, OnInit } from '@angular/core';
import { LocalserverService } from './localserver.service';
import { LocalServer } from './localserver';

@Component({
  selector: 'app-localserver',
  templateUrl: './localserver.component.html',
  styleUrls: ['./localserver.component.css']
})
export class LocalserverComponent implements OnInit {

  serversList: LocalServer[];
  server = new LocalServer(0, '', '', '');

  constructor(private serverService: LocalserverService) { }

  ngOnInit() {
    this.serverService.findAll().subscribe(
      serversList => this.serversList = serversList
    );
  }

  onSubmit() {
    this.serverService.createNew(this.server).subscribe(
      () => {this.serversList.push(this.server), this.ngOnInit()}
    )
  }

  delete(id: number) {
    this.serverService.deleteOld(id).subscribe(() => {
      this.ngOnInit();
    });
  }

}
