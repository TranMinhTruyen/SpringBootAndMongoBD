import { HttpClient } from '@angular/common/http';
import { Component, Inject } from '@angular/core';
import { CartComponent } from '../cart/cart.component';

declare var $: any;
@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent {
  constructor(private http: HttpClient, @Inject('BASE_URL') baseUrl: string) {
    this.SearchProduct();
    this.GetAllBrand();
    this.GetAllCategory();
    this.brandFilter = "";
    this.categoryFilter = "";
    this.priceFilter = 0;
    // this.getThucAn();
  }
  // thucan: any = {
  //     data: [],
  //     totalRecord: 0,
  //     page: 0,
  //     size: 0,
  //     totalPage: 0,
  //   }
  // getThucAn(){
  //   this.http.get<any>('http://localhost:8080/api/thucan/getAllThucAn/'+'?page='+1+ '&size=' + 1)
  //     .subscribe(
  //       result => {
  //                   var res: any = result;
  //                   console.log(res)
  //                 }
  //     );
  // }
  //#region "Khai báo các biến"
  isEdit: Boolean = false;
  selectBrand: any = [];
  selectCategory: any = [];
  allSize: Number = 2000000;
  allPage: Number = 1;
  checkloading: Boolean = false;
  priceFilter: Number = 0;
  brandFilter: String = "";
  categoryFilter: String = "";
  size: number = 8;
  page: number = 1;
  keyword: String = "";
  dateDisplay: String;
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
  nowDate = new Date();
  product: any = {
    productID: "",
    productName: "",
    price: 0,
    brandID: "1",
    categoryID: "1",
    createDate: this.nowDate.toJSON(),
    unit: "",
    unitsInStock: 0,
    discount: 0,
    description: "",
    picture: "",
    note: "",
    brandName: ""
  };

  amount: any = {
    productID: "",
    unitsInStock: 0,
  }
  //#endregion "Khai báo các biến"
  pic:any;


  //#region "Tìm sản phẩm"
  SearchProduct() {
    if (this.page < 0) {
      alert("Lỗi! Trang không hợp lệ!")
    }
    else {
      this.http.get<any>('https://localhost:44343/api/Product/Search_Product/' + this.size + ',' + this.page + '?keyword=' + this.keyword)
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


  //#region "Lấy thông tin nhãn hiệu"
  GetAllBrand() {
    this.http.get<any>('https://localhost:44343/api/Brand/Search_Brand/' + this.allSize + ',' + this.allPage).subscribe(
      result => {
                  var res: any = result;
                  if (res != null) {
                      this.selectBrand = res.data;
                  }
                  else {
                      alert(res.message);
                  }
                },
      error =>  {
                  alert("Server error!!")
                });
  }
  //#endregion "Lấy thông tin nhãn hiệu"


  //#region "Lấy thông tin danh mục"
  GetAllCategory() {
    this.http.get<any>('https://localhost:44343/api/Category/Search_Category/' + this.allSize + ',' + this.allPage).subscribe(
      result => {
                  var res: any = result;
                  if (res != null) {
                      this.selectCategory = res.data;
                  }
                  else {
                      alert(res.message);
                  }
                },
      error =>  {
                  alert("Server error!!")
                }
    );
  }
  //#endregion "Lấy thông tin danh mục"


  //#region "Lọc sản phẩm"
  FilterProduct() {
    this.http.get<any>('https://localhost:44343/api/Product/Filter_Product/' + 20 + ',' + this.page + '?price=' + this.priceFilter + '&brand=' + this.brandFilter + '&category=' + this.categoryFilter)
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

  specificInformation(index) {
    this.product = this.products.data[index];
    this.openSpecificInformationModal();
  }
  openSpecificInformationModal() {
    $('#specificInformationModal').modal('show');
  }

  closeSpecificInformationModal() {
    $('#openCreateUpdateModal').modal('toggle');
  }
  setDefaultPic(event) {
    event.target.src = 'assets/placeholder.png';
  }
}
//#region "JavaScript"
//#endregion "JavaScript"
