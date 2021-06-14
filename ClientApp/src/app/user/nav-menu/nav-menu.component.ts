import { HttpClient } from '@angular/common/http';
import { Component, Inject, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';

declare var $: any;

@Component({
  selector: 'app-nav-menu',
  templateUrl: './nav-menu.component.html',
  styleUrls: ['./nav-menu.component.css']
})
export class NavMenuComponent implements OnInit {
  isExpanded = false;
  constructor(private http: HttpClient, @Inject('BASE_URL') baseUrl: string, router: Router, private fb: FormBuilder) {
    this.rForm = fb.group({
      'username': [null, Validators.required],
      'pass': [null, Validators.required],   
    })
  }
  ngOnInit() {
    this.customer = sessionStorage.getItem('customer');
    this.customerLogin = Boolean(JSON.parse(sessionStorage.getItem('customerLogin')));
    console.log(this.customer);
  }
  //#region "Khai báo các biến"
  rForm: FormGroup;
  check: any;
  username: string = "";
  password: string = "";
  customer: String = "";
  checkSuccess: Boolean = true;
  checkActive: Boolean = true;
  customerLogin: Boolean = false;
  data: any = {
    account: "",
    pass: ""
  }
  req: any = {
    account: "",
    status: ""
  }
  userLogin: String = "Customer";
  //#endregion "Khai báo các biến"

  collapse() {
    this.isExpanded = false;
  }

  toggle() {
    this.isExpanded = !this.isExpanded;
  }

  openLoginModal() {
    $('#LoginModal').modal('show');
  }

  checkForm(check){
    this.data.account = check.account;
    this.data.pass = check.pass;
  }

  checkLogin() {
    this.data.account = this.username;
    this.data.pass = this.password;
    this.http.post('https://localhost:44343/api/Customer/Login', this.data).subscribe(
    result => {
      this.req = result;
      if (this.req.status == 1){
        this.checkActive = false;
        this.checkSuccess = true;
      }
      else{
        if (this.req.account == ""){
          this.checkSuccess = false;
        }
        else
        {
          this.checkActive = true;
          this.customerLogin = true;
          sessionStorage.setItem('customer', this.data.account);
          sessionStorage.setItem('customerLogin', "true")
          window.location.reload();
          this.toggleLoginModal();
        }
      }
    },
    error => {
      alert("Server error!!")
    });
  }

  logout(){
    sessionStorage.setItem('customerLogin', "false");
    sessionStorage.removeItem('customer');
    window.location.assign("http://" + window.location.host + "/");
  }

  toggleLoginModal() {
    $('#LoginModal').modal('hide');
    this.checkSuccess = true;
  }

}
//#region "JavaSrcipt"
//#endregion "JavaSrcipt"
