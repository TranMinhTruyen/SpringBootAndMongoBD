import { Component, Inject } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { formatDate } from '@angular/common';
declare var $: any;

@Component({
  selector: 'app-admin-products',
  templateUrl: './admin-products.component.html',
  styleUrls: ['./admin-products.component.css']
})

export class AdminProductsComponent {


  constructor(private http: HttpClient, @Inject('BASE_URL') baseUrl: string) {
    this.SearchProduct();
  }

  //#region "Khai báo các biến"
  isEdit: Boolean = false;
  checkloading: Boolean = false;
  size: number = 5;
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
  nowDate = new Date();
  product: any = {
    productID: "",
    productName: "",
    price: "",
    brandID: "1",
    categoryID: "1",
    createDate: this.nowDate.toJSON(),
    unit: "",
    unitsInStock: 0,
    discount: 0,
    description: "",
    picture: "null",
    note: "",
    brandName: ""
  };
  //#endregion "Khai báo các biến"


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
                      this.GetAllCategory();
                      this.GetAllBrand();
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
  

  //#region "Tạo sản phẩm"
  CreateProduct(){
    this.product.unitsInStock = parseInt(this.product.unitsInStock, 10);
    this.product.discount = parseInt(this.product.discount, 10);
    this.product.price = parseInt(this.product.price);
    this.http.post('https://localhost:44343/api/Product/Create_Product', this.product)
    .subscribe(
      result => {
                  var res:any = result;
                  if (res != null) {
                    this.products = res;
                    this.SearchProduct();
                    this.closeSpecificInformationModal();
                    alert("Đã thêm sản phẩm mới!");
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
  //#endregion "Tạo sản phẩm"


  //#region "Xóa sản phẩm"
  DeleteProduct(productID){
    var check = confirm("Bạn có muốn xóa sản phẩm này ?")
    if (check == true) {
      this.http.delete('https://localhost:44343/api/Product/Delete_Product/' + productID)
      .subscribe(
        result => {
                    var res: any = result;
                    if (res != null){
                      this.products = res;
                      alert("Đã xóa sản phẩm!");
                      this.SearchProduct();
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
  //#endregion "Xóa sản phẩm"


  //#region "Sửa sản phẩm"
  UpdateProduct(){
    this.product.unitsInStock = parseInt(this.product.unitsInStock, 10);
    this.product.discount = parseInt(this.product.discount, 10);
    this.product.price = parseInt(this.product.price);
    this.http.put('https://localhost:44343/api/Product/Update_Product/' + this.product.productID, this.product)
    .subscribe(
      result => {
                  var res:any = result;
                  if (res != null) {
                    this.products = res;
                    alert("Đã sửa sản phẩm!");
                    this.closeSpecificInformationModal();
                    this.SearchProduct();
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
  //#endregion "Sửa sản phẩm"
  

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
  
  
  //#region "Các hàm của Modal"
  specificInformation(index) {
    this.product = this.products.data[index];
    this.openSpecificInformationModal();
  }

  openCreateUpdateModal(isEdit, index)
  {
    this.isEdit = isEdit;
    $('#openCreateUpdateModal').modal('show');
    this.checkEdit(isEdit, index);
    this.dateDisplay = formatDate(this.nowDate.toJSON(),'dd/MM/yyyy','en-US');
  }

  openSpecificInformationModal() {
    $('#specificInformationModal').modal('show');
  }

  closeSpecificInformationModal() {
    $('#openCreateUpdateModal').modal('toggle');
  }

  checkEdit(isEdit, index) {
    if (isEdit) {
        this.product = this.products.data[index];
    }
    else {
        this.product = {
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
          picture: "null",
          note: "",
        }
    }
  }

  selectCategory: any = [];
  selectBrand: any = [];
  allSize: Number = 2000000;
  allPage: Number = 1;
  GetAllCategory() {
      this.http.get<any>('https://localhost:44343/api/Category/Search_Category/'
          + this.allSize + ',' + this.allPage).subscribe(
              result => {
                  var res: any = result;
                  if (res != null) {
                      this.selectCategory = res.data;
                  }
                  else {
                      alert(res.message);
                  }
              },
              error => {
                  alert("Server error!!")
              });
  }
  GetAllBrand() {
      this.http.get<any>('https://localhost:44343/api/Brand/Search_Brand/'
          + this.allSize + ',' + this.allPage).subscribe(
              result => {
                  var res: any = result;
                  if (res != null) {
                      this.selectBrand = res.data;
                  }
                  else {
                      alert(res.message);
                  }
              },
              error => {
                  alert("Server error!!")
              });
  }
  //#endregion "Các hàm của Modal"
}
//#region "JavaSrcipt"
//#endregion "JavaSrcipt"