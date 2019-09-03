import { Component, OnInit } from '@angular/core';
import {Router} from '@angular/router';
import {AuthenticationService} from '../../services/authentication.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit {
  
  constructor(
    private authService: AuthenticationService,
    private router: Router
  ) {}


  title = 'business-systems-security-frontend';
  
  isAdmin: boolean;

  ngOnInit(): void {
    
    // if ( authority === 'admin')
    // isAdmin = true



  }

  loggedIn(): boolean {
    return this.authService.isLoggedIn();
  }

  logout(): void {
    this.authService.logout();
    this.router.navigate(['login']);
  }
}
