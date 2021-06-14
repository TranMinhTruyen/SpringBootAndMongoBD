import { Component, Inject } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
declare var $: any;

@Component({
  selector: 'app-admin-brand',
  templateUrl: './admin-brand.component.html',
  styleUrls: ['./admin-brand.component.css']
})

export class AdminBrandComponent {
  constructor(private http: HttpClient, @Inject('BASE_URL') baseUrl: string) {
    this.SearchBrand();
  }
  //#region "Khai báo các biến"
  isEdit: Boolean = false;
  checkloading: Boolean = false;
  size: number = 5;
  page: number = 1;
  keyword: String = "";
  dateDisplay: String;
  brands: any = {
    data: [],
    totalRecord: 0,
    page: 0,
    size: this.size,
    totalPage: 0,
  }
  nowDate = new Date();
  brand: any = {
    brandID: "",
    brandName: "",
    createDay: this.nowDate.toJSON(),
    description: "",
    picture: "",
    note: "",
  };
  //#endregion "Khai báo các biến"


  //#region "Tìm nhãn hiệu"
  SearchBrand() {
    if (this.page < 0) {
      alert("Lỗi! Trang không hợp lệ!")
    }
    else {
      this.http.get<any>('https://localhost:44343/api/Brand/Search_Brand/' + this.size + ',' + this.page + '?keyword=' + this.keyword)
      .subscribe(
        result => {
                    var res: any = result;
                    if (res != null) {
                      if (this.checkloading == true && this.page > res.totalPage){
                        if (res.totalRecord > 0){
                          alert("Trang không tồn tại")
                        }
                        else{
                          this.brands = res;
                        }
                      }
                      else{
                        this.brands = res;
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
  //#endregion "Tìm nhãn hiệu"


  //#region "Tạo nhãn hiệu"
  CreateBrand(){
    if (this.brand.brandID == "" || this.brand.brandName == ""){
      alert("Lỗi! Phải nhập những trường có dấu *");
    }
    else{
      this.http.post('https://localhost:44343/api/Brand/Create_Brand', this.brand)
      .subscribe(
        result => {
                    var res:any = result;
                    if (res != null) {
                      this.brands = res;
                      alert("Đã thêm nhãn hiệu mới!");
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
  //#endregion "Tạo nhãn hiệu"


  //#region "Xóa nhãn hiệu"
  DeleteBrand(brandID){
    var check = confirm("Bạn có muốn xóa nhãn hiệu này ?")
    if (check == true) {
      this.http.delete('https://localhost:44343/api/Brand/Delete_Brand/' + brandID)
      .subscribe(
        result => {
                    var res: any = result;
                    if (res != null){
                      this.brands = res;
                      alert("Đã xóa sản phẩm!");
                      this.SearchBrand();
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
  //#endregion "Xóa nhãn hiệu"


  //#region "Sửa nhãn hiệu"
  UpdateBrand(){
    this.http.put('https://localhost:44343/api/Brand/Update_Brand/' + this.brand.brandID, this.brand)
    .subscribe(
      result => {
                  var res:any = result;
                  if (res != null) {
                    this.brands = res;
                    alert("Đã sửa nhãn hiệu!");
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
  //#endregion "Sửa nhãn hiệu"


  //#region "Phân trang"
  goNext() {
    if (this.brands.page < this.brands.totalPage) {
      this.page = parseInt("" + this.page) + 1; 
      this.SearchBrand();
    }
    else {
      alert("Bạn đang ở trang cuối !!!")
    }
  }


  goPrevious() {
    if (this.brands.page > 1) {
      this.page = parseInt("" + this.page) - 1; 
      this.SearchBrand();  
    }
    else {
      alert("Bạn đang ở trang đầu !!!")
    }
  }
  //#endregion "Phân trang"


  //#region "Các hàm của Modal"
  specificInformation(index) {
    this.brand = this.brands.data[index];
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

  checkEdit(isEdit, index) {
    if (isEdit) {
        this.brand = this.brands.data[index];
    }
    else {
        this.brand = {
          brandID: "",
          brandName: "",
          createDay: this.nowDate.toJSON(),
          description: "",
          picture: "",
          note: "",
        }
    }
  }
  //#endregion "Các hàm của Modal"
}
