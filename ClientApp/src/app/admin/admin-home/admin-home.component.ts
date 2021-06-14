import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-admin-home',
  templateUrl: './admin-home.component.html',
})
export class AdminHomeComponent {
  ngOnInit() {
    this.account = sessionStorage.getItem('admin');
    this.isLogin = Boolean(JSON.parse(sessionStorage.getItem('adminLogin')));
    if (sessionStorage.getItem('customer') != null){
      this.isCustomer = true;
    }
    else{
      this.isCustomer = false;
    }
    console.log(this.account);
  }
  //#region "Khai báo các biến"
  account: String = ""
  isCustomer: Boolean = false;
  isLogin: Boolean = false;
  userLogin: String = "Admin";
  //#endregion "Khai báo các biến"
}
