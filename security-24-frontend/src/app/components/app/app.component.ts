import { Component, OnInit } from '@angular/core';
import {Router} from '@angular/router';
import { TokenStorageService } from 'src/app/auth/token-storage.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit {
  
  constructor(
    private tokenStorage: TokenStorageService,
    private router: Router
  ) {}


  title = 'business-systems-security-frontend';
  
  isLoggedIn: boolean;
  isAdmin: boolean;

  ngOnInit(): void {

    if (this.tokenStorage.getToken() != null) {
      this.isLoggedIn = true;

      if (this.tokenStorage.getAuthorities().includes('ROLE_SECURITY_ADMIN'))
        this.isAdmin = true;
    }
  }

  logout(): void {
    this.tokenStorage.signOut();
    this.isLoggedIn = false;
    this.router.navigate(['login']);
  }
}
