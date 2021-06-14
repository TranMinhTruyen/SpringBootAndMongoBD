import { HttpClient } from '@angular/common/http';
import { Component, Inject } from '@angular/core';
import { Router } from '@angular/router';

declare var $: any;
@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})
export class RegisterComponent {
  constructor(private http: HttpClient, @Inject('BASE_URL') baseUrl: string, private router?: Router) {
  }
  //#region "Khai báo các biến"
  nowDate = new Date();
  size: number = 5;
  page: number = 1;
  keyword: String = "";
  confirmPass: String = "";
  account: any = {
    account: "",
    pass: "",
    lastName: "",
    firstName: "",
    dateOfBirth: "",
    gender: "",
    address: "",
    phoneNumberOfCustomer: "",
    email: "",
    createdDate: this.nowDate.toJSON(),
    accountStatus: "Active",
    note: "",
  };
  accounts: any = {
    data: [],
    totalRecord: 0,
    page: 0,
    size: this.size,
    totalPage: 0,
  }
  customer: String = "";
  customerLogin: Boolean = false;
  data: any = {
    account: "",
    pass: ""
  }
  userLogin: String = "Customer";
  //#endregion "Khai báo các biến"


  //#region "Tạo tài khoản"
  CreateAccount(){
    if (this.account.pass != this.confirmPass){
      alert("Mật khẩu và xác nhận mật khẩu không trùng nhau")
    }
    else{
      this.http.post('https://localhost:44343/api/Customer/Create_Customer', this.account)
      .subscribe(
        result => {
                    var res:any = result;
                    if (res != null) {
                      this.accounts = res;
                      alert("Đã tạo tài khoản");
                    }
                    else {
                      alert("Tài khoản đã tồn tại");
                    }
                  },
        error =>{
                  alert("Tài khoản đã tồn tại");
                }
        );
    }
  }
  //#endregion "Tạo tài khoản"

  openLoginModal() {
    $('#LoginModal').modal('show');
  }
}