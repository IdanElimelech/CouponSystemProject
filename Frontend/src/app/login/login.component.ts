import { Component, OnInit, ViewChild } from '@angular/core';
import { NgForm } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { Observable } from 'rxjs';
import { StorageServiceComponent } from '../storage-service.component';
import { Token } from '../token'
import { LoginService } from './login-service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css'],
  providers: [LoginService]
})

export class LoginComponent implements OnInit {
  // Check when compiled
  TYPE_CUSTOMER = 1
  TYPE_COMPANY = 2
  TYPE_ADMIN = 3
  @ViewChild("f") loginForm: NgForm

  email: string
  password: string
  type: number

  token: Token

  loggedIn: boolean
  loginFailed: boolean

  constructor(private loginService: LoginService, private router: Router, private route: ActivatedRoute) {
  }

  ngOnInit(): void {
    this.loggedIn = false
    this.loginFailed = false
  }

  onCancelClicked() {

  }

  onLogin() {
    this.setLoginParams()
    this.loginService.login(this.email, this.password, this.type).subscribe(token => {
      if (token != undefined) {

        this.token = token
        this.loggedIn = true
        this.navigateToRelevantPage()
      }

    })
    // Wanted to do a simple if/else but the else statement did not execute for some reason
    if (this.token == undefined) {
      this.loginFailed = true
    }
  }

  setLoginParams() {
    this.email = this.loginForm.value.email
    this.password = this.loginForm.value.password
    this.type = this.loginForm.value.loginType
  }

  navigateToRelevantPage() {
    if (this.loggedIn) {
      switch (true) {
        case this.type == this.TYPE_CUSTOMER:
          this.router.navigate(["/customer/" + this.token.token], { relativeTo: this.route })
          break
        case this.type == this.TYPE_COMPANY:
          this.router.navigate(["/company/" + this.token.token], { relativeTo: this.route })
          break
        case this.type == this.TYPE_ADMIN:
          this.router.navigate(["/admin/" + this.token.token], { relativeTo: this.route })
          break

        default:
          this.router.navigate([""], { relativeTo: this.route })
      }
    }
  }

}
