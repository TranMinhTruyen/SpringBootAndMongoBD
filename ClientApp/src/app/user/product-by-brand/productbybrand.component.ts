import { HttpClient } from '@angular/common/http';
import { isNull } from '@angular/compiler/src/output/output_ast';
import { Component, Inject } from '@angular/core';
import { ActivatedRoute } from '@angular/router'; 
import { CartComponent } from '../cart/cart.component';
import { ProductByBrandService } from './productbybrand.service';

declare var $: any;
@Component({
  selector: 'app-product-by-brand',
  templateUrl: './productbybrand.component.html',
  styleUrls: ['./productbybrand.component.css'],
})
export class ProductByBrandComponent {
  constructor(private http: HttpClient, private productByBrandService: ProductByBrandService, @Inject('BASE_URL') baseUrl: string, private route: ActivatedRoute) {
  }
  ngOnInit() { 
    this.route.queryParams.subscribe( 
      params => { 
          this.id = params['brandID']; 
          this.name = params['brandName']
      });
      console.log(this.id);
      this.SearchProduct();
      this.priceFilter = 0;
  }
  //#region "Khai báo các biến"
  id: String = "";
  name: String = "";
  nowDate = new Date();
  checkloading: Boolean = false;
  size: number = 8;
  page: number = 1;
  priceFilter: Number = 0;
  categoryFilter: String = "";
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
    this.products = this.productByBrandService.getByKeyword(this.size, this.page, this.id)
        .subscribe(response => console.log(response))
    if (isNull(this.products)){
      alert("Không tìm thấy sản phẩm");
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
    this.http.get<any>('https://localhost:44343/api/Product/Filter_Product/' + 20 + ',' + this.page + '?price=' + this.priceFilter + '&brand=' + this.id + '&category=' + this.categoryFilter)
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