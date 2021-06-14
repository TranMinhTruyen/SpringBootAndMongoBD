import { HttpClient } from '@angular/common/http';
import { Component, Inject } from '@angular/core';
import { CartComponent } from '../cart/cart.component';
import { ActivatedRoute } from '@angular/router'; 

@Component({
  selector: 'app-product-by-category',
  templateUrl: './productbycategory.component.html',
  styleUrls: ['./productbycategory.component.css']
})
export class ProductByCategoryComponent {
  constructor(private http: HttpClient, @Inject('BASE_URL') baseUrl: string, private route: ActivatedRoute) {
  }
  ngOnInit() { 
    this.route.queryParams.subscribe( 
      params => { 
          this.id = params['categoryID']; 
          this.name = params['categoryName'];
      });
      console.log(this.id);
      this.SearchProduct();
      this.priceFilter = 0;
  }
  //#region "Khai báo các biến"
  id: String = "";
  name: String= "";
  nowDate = new Date();
  checkloading: Boolean = false;
  priceFilter: Number = 0;
  brandFilter: String = "";
  size: number = 8;
  page: number = 1;
  products: any = {
      data: [],
      totalRecord: 0,
      page: 0,
      size: this.size,
      totalPage: 0,
  }
  cart: any = {
      account: "",
      productID: "",
      amounts: 1,
      note: ""
  }
  amount: any = {
      productID: "",
      unitsInStock: 0,
  }
  //#endregion "Khai báo các biến"


  //#region "Tìm sản phẩm"
  SearchProduct() {
    if (this.page < 0) {
      alert("Lỗi! Trang không hợp lệ!")
    }
    else {
      this.http.get<any>('https://localhost:44343/api/Product/Get_Product_By_Category/'  + this.size + ',' + this.page + '?categoryID=' + this.id)
      .subscribe(
        result => {
                    var res: any = result;
                    if (res != null) {
                      if (this.checkloading == true && this.page > res.totalPage){
                        if (res.totalRecord > 0){
                          alert("Trang không tồn tại")
                        }
                        else{
                          this.products = res;
                        }
                      }
                      else{
                        this.products = res;
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
  //#endregion "Tìm sản phẩm"


  //#region "Phân trang"
  goNext() {
    if (this.products.page < this.products.totalPage) {
      this.page = parseInt("" + this.page) + 1; 
      this.SearchProduct();
    }
    else {
      alert("Bạn đang ở trang cuối !!!")
    }
  }


  goPrevious() {
    if (this.products.page > 1) {
      this.page = parseInt("" + this.page) - 1; 
      this.SearchProduct();  
    }
    else {
      alert("Bạn đang ở trang đầu !!!")
    }
  }
  //#endregion "Phân trang"


  //#region "Thêm vào giỏ hàng"
  ShoppingCart: CartComponent
  addProductToCart(productID: String, unitsInStock: number)
  {
    try {
      this.cart.account = sessionStorage.getItem('customer');
    }
    catch{
      this.cart.account = null;
    }


    if (this.cart.account == null){
      alert("Bạn phải đăng nhập");
    }
    else{
      if (unitsInStock == 0){
        alert("Sản phẩm hết hàng")
      }
      else{
        this.cart.account = sessionStorage.getItem('customer');
        this.cart.productID = productID;
        this.http.post('https://localhost:44343/api/Cart/Create_Cart', this.cart).subscribe(
          result => {
            var res: any = result;
            if (res != null){
              alert("Thêm vào giỏ hàng thành công");
              this.UpdateAmount(productID,unitsInStock - 1);
              window.location.reload();
            }
            else{
              alert("Lỗi dữ liệu");
            }
          },
          error =>  {
            alert("Bạn đã thêm sản phẩm này vào giỏ hàng rồi");
          }
        )
      }
    }
  }
  //#endregion "Thêm vào giỏ hàng"


  //#region "Cập nhật số lượng khi thêm vào giỏ hàng"
  UpdateAmount(productID: String, unitsInStock: number){
    this.amount.productID = productID;
    this.amount.unitsInStock = unitsInStock;
    this.http.put('https://localhost:44343/api/Product/Update_Unit_In_Stock/' + productID, this.amount).subscribe(
      result => {
        var res: any = result;
      },
      error =>  {
        alert("Error");
      }
    )
  }
  //#endregion "Cập nhật số lượng khi thêm vào giỏ hàng"


  //#region "Lọc sản phẩm"
  FilterProduct() {
    this.http.get<any>('https://localhost:44343/api/Product/Filter_Product/' + 20 + ',' + this.page + '?price=' + this.priceFilter + '&brand=' + this.brandFilter + '&category=' + this.id)
    .subscribe(
      result => {
                  var res: any = result;
                  if (res != null) {
                    if (this.checkloading == true && this.page > res.totalPage){
                      if (res.totalRecord > 0){
                        alert("Trang không tồn tại")
                      }
                      else{
                        this.products = res;
                      }
                    }
                    else{
                      this.products = res;
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
  //#endregion "Lọc sản phẩm"
  setDefaultPic(event) {
    event.target.src = 'assets/placeholder.png';
  }
}