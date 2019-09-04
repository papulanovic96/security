import { Component, OnInit } from '@angular/core';

import { Router } from '@angular/router';
import {ToastrService} from 'ngx-toastr';

import { SigninRequest } from 'src/app/models/login-response';
import { AuthService } from 'src/app/auth/auth.service';
import { TokenStorageService } from 'src/app/auth/token-storage.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css',
              './css/main.css',
              './css/util.css'],
})
export class LoginComponent implements OnInit {
  public wrongUsernameOrPass: boolean;
  public showSpinner: boolean;

  public request = new SigninRequest;

  isLoggedIn: boolean;
  roles: string[];

  constructor(
    private authenticationService: AuthService,
    private tokenStorage: TokenStorageService,
    private router: Router,
    private toastrService: ToastrService
  ) {
    this.wrongUsernameOrPass = false;
  }

  ngOnInit() {
    if (this.tokenStorage.getToken()) {
      this.isLoggedIn = true;
      this.roles = this.tokenStorage.getAuthorities();
    }
  }

  login(): void {

    this.authenticationService
      .signIn(this.request)
      .subscribe(
        data => {
          this.tokenStorage.saveToken(data.accessToken);
          this.tokenStorage.saveUsername(data.username);
          this.tokenStorage.saveAuthorities(data.grantedAuthorities);

          this.isLoggedIn = true;
          this.roles = this.tokenStorage.getAuthorities();

          if (!this.tokenStorage.getAuthorities().includes('ROLE_SECURITY_ADMIN'))
            this.router.navigate(['404']);

          this.router.navigate(['/']);
        }
        ,
        err => {
          this.showSpinner = false;
          this.toastrService.error(err.error.message);
          this.wrongUsernameOrPass = true;
        }
      );
  }
}
