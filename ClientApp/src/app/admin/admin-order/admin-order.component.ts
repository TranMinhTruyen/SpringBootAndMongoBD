import { Component, Inject } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
declare var $: any;

@Component({
  selector: 'app-admin-order',
  templateUrl: './admin-order.component.html',
  styleUrls: ['./admin-order.component.css']
})

export class AdminOrderComponent {
  constructor(private http: HttpClient, @Inject('BASE_URL') baseUrl: string) {
    this.SearchOrder();
  }
  //#region "Khai báo các biến"
  isEdit: Boolean = false;
  checkloading: Boolean = false;
  size: number = 5;
  page: number = 1;
  keyword: number = 0;
  dateDisplay: String;
  orders: any = {
    data: [],
    totalRecord: 0,
    page: 0,
    size: this.size,
    totalPage: 0,
  }
  nowDate = new Date();
  order: any = {
    orderID: 0,
    name: "",
    createdDate: this.nowDate.toJSON(),
    phoneNumberOfOrder: "",
    address: "",
    status: "",
  };
  //#endregion "Khai báo các biến"


  //#region "Tìm đơn hàng"
  SearchOrder() {
    if (this.page < 0) {
      alert("Lỗi! Trang không hợp lệ!")
    }
    else {
      this.http.get<any>('https://localhost:44343/api/Order/Search_Order/' + this.size + ',' + this.page + '?keyword=' + this.keyword)
      .subscribe(
        result => {
                    var res: any = result;
                    if (res != null) {
                      if (this.checkloading == true && this.page > res.totalPage){
                        if (res.totalRecord > 0){
                          alert("Trang không tồn tại")
                        }
                        else{
                          this.orders = res;
                        }
                      }
                      else{
                        this.orders = res;
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
  //#endregion "Tìm đơn hàng"


  //#region "Xóa đơn hàng"
  DeleteOrder(orderID){
    var check = confirm("Bạn có muốn xóa đơn hàng này ?")
    if (check == true) {
      this.http.delete('https://localhost:44343/api​/Order​/Delete_Order​/' + orderID)
      .subscribe(
        result => {
                    var res: any = result;
                    if (res != null){
                      this.orders = res;
                      alert("Đã xóa đơn hàng!");
                      this.SearchOrder();
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
  //#endregion "Xóa đơn hàng"


  //#region "Sửa đơn hàng"
  UpdateOrder(){
    this.http.put('https://localhost:44343/api/Order/Update_Order/' + this.order.orderID, this.order)
    .subscribe(
      result => {
                  var res:any = result;
                  if (res != null) {
                    this.orders = res;
                    alert("Đã sửa đơn hàng!");
                    this.SearchOrder();
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
  //#endregion "Sửa đơn hàng"


  //#region "Phân trang"
  goNext() {
    if (this.orders.page < this.orders.totalPage) {
      this.page = parseInt("" + this.page) + 1; 
      this.SearchOrder();
    }
    else {
      alert("Bạn đang ở trang cuối !!!")
    }
  }


  goPrevious() {
    if (this.orders.page > 1) {
      this.page = parseInt("" + this.page) - 1; 
      this.SearchOrder();  
    }
    else {
      alert("Bạn đang ở trang đầu !!!")
    }
  }
  //#endregion "Phân trang"


  //#region "Các hàm của Modal"
  specificInformation(index) {
    this.order = this.orders.data[index];
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
        this.order = this.orders.data[index];
    }
    else {
        this.order = {
          orderID: 0,
          name: "",
          createdDate: this.nowDate.toJSON(),
          phoneNumberOfOrder: "",
          address: "",
          city: "",
          status: "",
        }
    }
  }
  //#endregion "Các hàm của Modal"
}