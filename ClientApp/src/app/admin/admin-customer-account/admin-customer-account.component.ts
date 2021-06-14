import { Component, Inject } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { DatePipe, formatDate } from '@angular/common';
declare var $: any;

@Component({
  selector: 'app-admin-customer-account',
  templateUrl: './admin-customer-account.component.html',
  styleUrls: ['./admin-customer-account.component.css']
})

export class AdminCustomerAccountComponent {
  constructor(private http: HttpClient, @Inject('BASE_URL') baseUrl: string) {
    this.SearchAccount();
  }
  //#region "Khai báo các biến"
  isEdit: Boolean = false;
  checkloading: Boolean = false;
  size: number = 5;
  page: number = 1;
  keyword: String = "";
  dateDisplay: String;
  accounts: any = {
    data: [],
    totalRecord: 0,
    page: 0,
    size: this.size,
    totalPage: 0,
  }
  nowDate = new Date();
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
    accountStatus: "",
    note: "",
  };
  //#endregion "Khai báo các biến"

  //#region "Tìm tài khoản"
  SearchAccount() {
    if (this.page < 0) {
      alert("Lỗi! Trang không hợp lệ!")
    }
    else {
      this.http.get<any>('https://localhost:44343/api/Customer/Search_Customer/' + this.size + ',' + this.page + '?keyword=' + this.keyword)
      .subscribe(
        result => {
                    var res: any = result;
                    if (res != null) {
                      if (this.checkloading == true && this.page > res.totalPage){
                        if (res.totalRecord > 0){
                          alert("Trang không tồn tại")
                        }
                        else{
                          this.accounts = res;
                        }
                      }
                      else{
                        this.accounts = res;
                      }
                      this.checkloading = true;
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
  }
  //#endregion "Tìm tài khoản"


  //#region "Tạo tài khoản"
  CreateAccount(){
    this.http.post('https://localhost:44343/api/Customer/Create_Customer', this.account)
    .subscribe(
      result => {
                  var res:any = result;
                  if (res != null) {
                    this.accounts = res;
                    this.SearchAccount();
                    this.closeSpecificInformationModal();
                    alert("Đã thêm tài khoản mới!");
                  }
                  else {
                    alert(res.message);
                  }
                },
      error =>{
                alert("Server error!");
              }
      );
  }
  //#endregion "Tạo tài khoản"


  //#region "Xóa tài khoản"
  DeleteAccount(account){
    var check = confirm("Bạn có muốn xóa tài khoản này ?")
    if (check == true) {
      this.http.delete('https://localhost:44343/api/Customer/Delete_Customer/' + account)
      .subscribe(
        result => {
                    var res: any = result;
                    if (res != null){
                      this.accounts = res;
                      alert("Đã xóa tài khoản!");
                      this.SearchAccount();
                    }
                    else {
                      alert(res.message);
                    }
                  },
        error =>{
                  alert("Server error!");
                }
      );
    }
  }
  //#endregion "Xóa tài khoản"


  //#region "Sửa tài khoản"
  UpdateAccount(){
    this.http.put('https://localhost:44343/api/Customer/Update_Customer/' + this.account.account, this.account)
    .subscribe(
      result => {
                  var res:any = result;
                  if (res != null) {
                    this.accounts = res;
                    alert("Đã sửa tài khoản!");
                    this.SearchAccount();
                    this.closeSpecificInformationModal();
                  }
                  else {
                    alert(res.message);
                  }
                },
      error =>{
                alert("Server error !");
              }
      );
  }
  //#endregion "Sửa tài khoản"
  

  //#region "Phân trang"
  goNext() {
    if (this.accounts.page < this.accounts.totalPage) {
      this.page = parseInt("" + this.page) + 1; 
      this.SearchAccount();
    }
    else {
      alert("Bạn đang ở trang cuối !!!")
    }
  }


  goPrevious() {
    if (this.accounts.page > 1) {
      this.page = parseInt("" + this.page) - 1; 
      this.SearchAccount();  
    }
    else {
      alert("Bạn đang ở trang đầu !!!")
    }
  }
  //#endregion "Phân trang"


  //#region "Các hàm của Modal"
  specificInformation(index) {
    this.account = this.accounts.data[index];
    this.openSpecificInformationModal();
  }

  openCreateUpdateModal(isEdit, index)
  {
    this.isEdit = isEdit;
    $('#openCreateUpdateModal').modal('show');
    this.checkEdit(isEdit, index);
    this.dateDisplay = this.nowDate.toJSON();
  }                           

  openSpecificInformationModal() {
    $('#specificInformationModal').modal('show');
  }

  closeSpecificInformationModal() {
    $('#openCreateUpdateModal').modal('toggle');
  }

  checkEdit(isEdit, index) {
    if (isEdit) {
        this.account = this.accounts.data[index];
    }
    else {
        this.account = {
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
          accountStatus: "",
          note: "",
        }
    }
  }
  //#endregion "Các hàm của Modal"
}