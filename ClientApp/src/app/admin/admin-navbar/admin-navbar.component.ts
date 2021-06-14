import { Component, Inject, OnInit } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Router } from '@angular/router';
declare var $: any;


@Component({
  selector: 'app-admin-navbar',
  templateUrl: './admin-navbar.component.html',
  styleUrls: ['./admin-navbar.component.css']
})
export class AdminNavBarComponent {
  isExpanded = false;
  constructor(private http: HttpClient, @Inject('BASE_URL') baseUrl: string, router: Router) {
  }
  ngOnInit() {
    this.account = sessionStorage.getItem('admin');
    this.isLogin = Boolean(JSON.parse(sessionStorage.getItem('adminLogin')));
    console.log(this.account);
    if (sessionStorage.getItem('customer') != null){
      this.isCustomer = true;
    }
    else{
      this.isCustomer = false;
    }
    this.CheckAdminAccount();
  }
  //#region "Khai báo các biến"
  router: Router
  nowDate = new Date();
  isCustomer: Boolean = false;
  account: String = ""
  isLogin: Boolean = false;
  data: any = {
    account: "",
    pass: ""
  }
  req: any ={
    account: "",
    status: ""
  }
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
  checkLogin() {this.http.post('https://localhost:44343/api/Employee/Login', this.data).subscribe(
      result => {
        this.req = result;
        if (this.req.status != 0){
          alert("Tài khoản bị vô hiệu hóa !")
        }
        else{
          if (this.req.account != "") {
            this.isLogin = true;
            sessionStorage.setItem('admin', this.data.account);
            sessionStorage.setItem('adminLogin', "true")
            window.location.href=window.location.href;
            this.toggleLoginModal();
          }
          else {
            alert("Tài khoản hoặc mật khẩu không đúng !")
          }
        }
      },
      error => {
        alert("Server error !!!")
      })
  }

  logout(){
    sessionStorage.setItem('adminLogin', "false");
    sessionStorage.removeItem('admin');
    this.router.navigate(['/admin']);
  }

  toggleLoginModal() {
    $('#LoginModal').modal('hide');
  }



  //#region "Tạo tài khoản admin"
  CreateAccount(){
    var account: any = {
      account: "admin",
      pass: "123",
      lastName: "admin",
      firstName: "first",
      dateOfBirth:"none",
      gender: "none",
      citizenIdentification: "none",
      address: "none",
      phoneNumberOfEmployee: "none",
      email: "none",
      createdDate: this.nowDate.toJSON(),
      accountStatus: "0",
      note: "none",
    };
      this.http.post('https://localhost:44343/api/Employee/Create_Employee', account).subscribe();
  }
  //#endregion "Tạo tài khoản admin"


  //#region "Kiểm tra tài khoản admin có hay chưa"
  CheckAdminAccount(){
    this.http.get<any>('https://localhost:44343/api/Employee/GetEmployeeByAccount/admin')
    .subscribe(
      result => {
                  var res: any = result;
                  if (res.account == "null") {
                    this.CreateAccount();
                  }
                },
    );
  }
  //#endregion "Kiểm tra tài khoản admin có hay chưa"
}
//#region "JavaSrcipt"
//#endregion "JavaSrcipt"