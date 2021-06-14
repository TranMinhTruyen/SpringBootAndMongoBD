import { HttpClient } from '@angular/common/http';
import { Component, Inject } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-cart',
  templateUrl: './cart.component.html',
  styleUrls: ['./cart.component.css']
})
export class CartComponent {
  constructor(private http: HttpClient, @Inject('BASE_URL') baseUrl: string, private router?: Router) {
    this.getCartByAccount(this.userLogin);
  }

  //#region "Khai báo các biến"
  itemAmount: number = 1;
  userLogin: String = sessionStorage.getItem('customer');
  cartItemAmount: number[] = [];
  result: any = {
      cartList: []
  }

  amount: any ={
    account: this.userLogin,
    productIdList: [""],
    amount: [0]
  }

  amountProduct: any = {
    productID: "",
    unitsInStock: 0,
  }

  subtotal: number = 0;
  discount: number = 0;
  shipping: number = 0;
  VATtax: number = 0;
  total: number = 0;
  //#endregion "Khai báo các biến"


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


  //#region "Xóa sản phẩm trong giỏ hàng"
  removeItemFromCart(account: String, productId: String, amounts: number, unitsInStock: number)
  {
    this.http.delete<any>('https://localhost:44343/api/Cart/Delete_Product_In_Cart/' + account + ',' + productId).subscribe(
      result => {
        var res: any = result;
        if (res != null) {
            alert("Xóa thành công sản phẩm trong giỏ hàng");
            this.getCartByAccount(account);
            if (unitsInStock >= 1){
              this.UpdateAmount(productId, amounts + unitsInStock);
            }
            else{
              this.UpdateAmount(productId, amounts);
            }
        }
        else {
            alert("Lỗi dữ liệu");
        }
      },
      error => {
        alert("Server error!!")
      });
  }
  //#endregion "Xóa sản phẩm trong giỏ hàng"


  //#region "Cập nhật số lượng khi xóa khỏi giỏ hàng"
  UpdateAmount(productID: String, unitsInStock: number){
    this.amountProduct.productID = productID;
    this.amountProduct.unitsInStock = unitsInStock;
    this.http.put('https://localhost:44343/api/Product/Update_Unit_In_Stock/' + productID, this.amountProduct).subscribe(
      result => {
        var res: any = result;
      },
      error =>  {
        alert("Error");
      }
    )
  }
  //#region "Cập nhật số lượng khi xóa khỏi giỏ hàng"


  setAmountUp(index, unitsInStock: number)
  {
    var currenUnitInStock: Number = this.result.cartList[index].amounts;
    if (unitsInStock == 0) {
      alert("Số lượng kho chỉ còn " + currenUnitInStock  + " đôi");
      this.result.cartList[index].amounts = currenUnitInStock;
    }
    else{
      this.amount = {
        account: this.userLogin,
        productIdList: [this.result.cartList[index].productID],
        amount: [this.result.cartList[index].amounts += 1]
      }
      this.http.put('https://localhost:44343/api/Cart/Update_Cart/' + this.userLogin + ',' + index, this.amount).subscribe(
        result => {
          var res: any = result;
          if (res != null){
            this.UpdateAmount(this.result.cartList[index].productID, unitsInStock - 1);
            window.location.reload();
          }
          else {
            alert("Lỗi dữ liệu");
          }
        },
        error => {
          alert("Server error!!")
        }
      )
    }
  }

  setAmountDown(index, unitsInStock: number)
  {
    this.amount = {
      account: this.userLogin,
      productIdList: [this.result.cartList[index].productID],
      amount: [this.result.cartList[index].amounts -= 1]
    }
    if (this.amount.amount <= 0) {
      alert("Giá trị nhỏ nhất là 1");
      this.result.cartList[index].amounts = 1
    }
    else {
      this.http.put('https://localhost:44343/api/Cart/Update_Cart/' + this.userLogin + ',' + index, this.amount).subscribe(
        result => {
          var res: any = result;
          if (res != null){
            this.UpdateAmount(this.result.cartList[index].productID, unitsInStock + 1);
            window.location.reload();
          }
          else {
            alert("Lỗi dữ liệu");
          }
        },
        error => {
          alert("Server error!!")
        }
      )
    }
  }

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

  createReceipt()
  {
      this.router.navigate(['/receipt', {amountList: this.cartItemAmount}]);
  }

  setDefaultPic(event) {
    event.target.src = 'assets/placeholder.png';
  }
}