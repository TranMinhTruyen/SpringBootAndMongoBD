import { HttpClient } from '@angular/common/http';
import { Component, Inject, OnInit } from '@angular/core';

declare var $: any;
@Component({
  selector: 'app-profile',
  templateUrl: './customerprofile.component.html',
  styleUrls: ['./customerprofile.component.css']
})
export class ProfileComponent {
  constructor(private http: HttpClient, @Inject('BASE_URL') baseUrl: string) {
    this.customer = sessionStorage.getItem('customer');
    this.SearchAccount(this.customer);
  }
  ngOnInit(){
    document.getElementById("defaultOpen").click();
  }

  //#region "Khai báo các biến"
  customer: String = "";
  nowDate = new Date();
  account: any = {
    account: "",
    pass: "",
    lastName: "",
    firstName: "",
    dateOfBirth: "",
    gender: "",
    email: "",
    address: "",
    phoneNumberOfCustomer: "",
    createdDate: this.nowDate.toJSON(),
    accountStatus: "",
    note: "",
  };
  updateAccount: any = {
    account: "",
    pass: "",
    lastName: "",
    firstName: "",
    dateOfBirth: "",
    gender: "",
    email: "",
    address: "",
    phoneNumberOfCustomer: "",
    createdDate: this.nowDate.toJSON(),
    accountStatus: "Active",
    note: ""
  };
  order: any ={
    data: []
  }
  //#endregion "Khai báo các biến"

  update: any = {
    status: "0"
  }

  //#region "Tìm tài khoản"
  SearchAccount(cus) {
    this.http.get<any>('https://localhost:44343/api/Customer/GetCustomerByAccount/' + cus).subscribe(
      result => {
                  var res: any = result;
                  if (res != null) {
                    this.account = res;
                    this.GetOrder();
                  }
                  else {
                    alert(res.message);
                  } 
                },
      error =>  {
                  alert("Server error !");
                }
    );
  }
  //#endregion "Tìm tài khoản"


  //#region "Hủy đơn hàng"
  CancelOrder(id) {
    
    this.http.put('https://localhost:44343/api/Order/Update_Order_Status/' + id, this.update).subscribe(
      result => {
                  var res: any = result;
                  if (res != null) {
                    this.GetOrder();
                    alert("Đã hủy đơn hàng");
                  }
                  else {
                    alert(res.message);
                  } 
                },
      error =>  {
                  alert("Server error !");
                }
    );
  }
  //#endregion "Hủy đơn hàng"


  //#region "Đã nhận hàng"
  CompleteOrder(id) {
    this.update.status = "4"
    this.http.put('https://localhost:44343/api/Order/Update_Order_Status/' + id, this.update).subscribe(
      result => {
                  var res: any = result;
                  if (res != null) {
                    this.GetOrder();
                    alert("Đã nhận đơn hàng");
                  }
                  else {
                    alert(res.message);
                  } 
                },
      error =>  {
                  alert("Server error !");
                }
    );
  }
  //#endregion "Đã nhận hàng"


  //#region "Lấy đơn hàng"
  GetOrder() {
    this.http.get<any>('https://localhost:44343/api/Order/Get_Order_By_Customer' + '?cusid=' + this.account.account).subscribe(
      result => {
                  var res: any = result;
                  if (res != null) {
                    this.order = res;
                  }
                  else {
                    alert(res.message);
                  } 
                },
      error =>  {
                  alert("Server error !");
                }
    );
  }
  //#endregion "Lấy đơn hàng"


  //#region "Sửa tài khoản"
  UpdateAccount(){
    if (this.updateAccount.account == "" || this.updateAccount.pass == "" || this.updateAccount.lastName == "" || this.updateAccount.firstName == ""
    || this.updateAccount.dateOfBirth == "" || this.updateAccount.gender == "" || this.updateAccount.email == "" || this.updateAccount.address == ""
    || this.updateAccount.phoneNumberOfCustomer == ""){
      alert("Lỗi! Phải nhập đủ các trường")
    }
    else{
      this.http.put('https://localhost:44343/api/Customer/Update_Customer/' + this.customer, this.updateAccount)
      .subscribe(
        result => {
                    var res:any = result;
                    if (res != null) {
                      this.account = res;
                      alert("Đã sửa tài khoản!");
                    }
                    else {
                      alert(res.message);
                    }
                  },
        error =>{
                  alert("Tên đăng nhập đã tồn tại !");
                }
      );
    }  
  }
  //#endregion "Sửa tài khoản"



  openTabs(id, tabName){
    var i, tabcontent, tablinks;

    tabcontent = document.getElementsByClassName("tabcontent");
    for (i = 0; i < tabcontent.length; i++) {
      tabcontent[i].style.display = "none";
    }

    tablinks = document.getElementsByClassName("tablinks");
    for (i = 0; i < tablinks.length; i++) {
      tablinks[i].className = tablinks[i].className.replace(" active", "");
    }

    document.getElementById(tabName).style.display = "block";
    document.getElementById(id).className += " active";
  }
}
//#region "JavaSrcipt"
//#endregion "JavaSrcipt"
