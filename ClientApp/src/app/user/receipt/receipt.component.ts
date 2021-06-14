import { HttpClient } from '@angular/common/http';
import { Component, Inject } from '@angular/core';
import { Router } from '@angular/router';

declare var $: any;
@Component({
  selector: 'app-receipt',
  templateUrl: './receipt.component.html',
  styleUrls: ['./receipt.component.css']
})
export class ReceiptComponent {
  constructor(private http: HttpClient, @Inject('BASE_URL') baseUrl: string, private router?: Router) {
    this.getCartByAccount(this.userLogin);
    this.setPrice();
    this.SearchAccount(this.userLogin);
    
  }
  //#region "Khai báo các biến"
  userLogin: String = sessionStorage.getItem('customer');
  cartItemAmount: number[]= [];
  subtotal: number = 0;
  discount: number = 0;
  shipping: number = 0;
  VATtax: number = 0;
  total: number = 0;
  result: any = {
    cartList: []
  }
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
    note: ""
  };
  //#endregion "Khai báo các biến"


  //#region "Tìm tài khoản"
  SearchAccount(cus) {
    this.http.get<any>('https://localhost:44343/api/Customer/GetCustomerByAccount/' + cus).subscribe(
      result => {
                  var res: any = result;
                  if (res != null) {
                    this.account = res;
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


  //#region "Lấy giỏ hàng"
  getCartByAccount(account: String)
  {
    this.http.get<any>('https://localhost:44343/api/Cart/Get_Cart_By_Customer/'+ account).subscribe(
      result => {
        var res: any = result;
        if (res != null) {
          this.result = res;
          this.setPrice();
        }
        else {
          alert("Lỗi dữ liệu");
        }
      },
      error => {
        alert("Server error!!")
      });
  }
  //#endregion "Lấy giỏ hàng"


  //#region "Tạo đơn hàng"
  CreateCustomerOrder()
  {
    var order: any = {
        name: this.account.lastName + " " + this.account.firstName,
        createdDate: this.nowDate.toJSON(),
        phoneNumberOfOrder: this.account.phoneNumberOfCustomer,
        address: this.account.address,
        totalPrice: this.total,
        status: "1",
        note: this.account.note,
        cusId: this.account.account
    }
    this.http.post('https://localhost:44343/api/Order/Create_Order', order).subscribe(
      result => {
        var res: any = result;
        if (res != null) {
          order = res;
          this.createOrderDetail(order);
          this.deleleCartList();
          alert("Chúc mừng bạn đã đặt hàng thành công");
          window.location.assign("http://" + window.location.host + "/");
        }
        else {
          alert("Lỗi dữ liệu");
        }
      },
      error => {
                  alert("Server error!!");
      });
  }
  //#endregion "Tạo đơn hàng"


  //#region "Xóa đơn hàng"
  deleleCartList()
  {
    this.http.delete<any>('https://localhost:44343/api/Cart/Delete_Cart/' + this.userLogin).subscribe(
      result => {
          var res: any = result;
          if (res != null) {
              console.log(res);
          }
          else {
              alert("Lỗi dữ liệu");
          }
      },
      error => {
          alert("Không thể xóa. Server Error !!")
      });
  }
  //#endregion "Xóa đơn hàng"


  //#region "Tạo chi tiết đơn hàng"
  createOrderDetail(order: any)
  {
    var data = [];
    this.result.cartList.forEach(value => {
        var orderDetails: any = {
            orderID: order.orderID,
            productID: value.productID,
            amounts: value.amounts,
            note: ""
        };
        data.push(orderDetails);
    });
    this.http.post('https://localhost:44343/api/OrderDetails/createOrderDetails', data).subscribe(
      result => {
          var res: any = result;
          if (res != null) {
              console.log(res);
          }
          else {
              alert("Lỗi dữ liệu");
          }
      },
      error => {
          alert("Server error!!")
      });
  }
  //#endregion "Tạo chi tiết đơn hàng"


  //#region "Tính toán giá tiền"
  setPrice()
  {
    for(var i = 0; i < this.result.cartList.length; i++)
    {
      this.subtotal += parseInt(this.result.cartList[i].price) * this.result.cartList[i].amounts;
    }
    if (this.subtotal >= 100000)
    {
      this.shipping = 0; 
    }
    else 
    {
      this.shipping = 50000;    
    }
    this.discount = 0;
    this.VATtax = this.subtotal * 0.1;
    this.total = this.subtotal + this.VATtax + this.shipping;
  }
  //#endregion "Tính toán giá tiền"
}