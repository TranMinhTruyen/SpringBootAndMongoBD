import { Component, Inject } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
declare var $: any;

@Component({
  selector: 'app-admin-category',
  templateUrl: './admin-category.component.html',
  styleUrls: ['./admin-category.component.css']
})

export class AdminCategoryComponent {
  constructor(private http: HttpClient, @Inject('BASE_URL') baseUrl: string) {
    this.SearchCategory();
  }
  //#region "Khai báo các biến"
  isEdit: Boolean = false;
  checkloading: Boolean = false;
  size: number = 5;
  page: number = 1;
  keyword: String = "";
  dateDisplay: String;
  categorys: any = {
    data: [],
    totalRecord: 0,
    page: 0,
    size: this.size,
    totalPage: 0,
  }
  nowDate = new Date();
  category: any = {
    categoryID: "",
    categoryName: "",
    createDay: this.nowDate.toJSON(),
    description: "",
    note: ""
  };
  //#endregion "Khai báo các biến"


  //#region "Tìm danh mục"
  SearchCategory() {
    if (this.page < 0) {
      alert("Lỗi! Trang không hợp lệ!")
    }
    else {
      this.http.get<any>('https://localhost:44343/api/Category/Search_Category/' + this.size + ',' + this.page + '?keyword=' + this.keyword)
      .subscribe(
        result => {
                    var res: any = result;
                    if (res != null) {
                      if (this.checkloading == true && this.page > res.totalPage){
                        if (res.totalRecord > 0){
                          alert("Trang không tồn tại")
                        }
                        else{
                          this.categorys = res;
                        }
                      }
                      else{
                        this.categorys = res;
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
  //#endregion "Tìm danh mục"


  //#region "Tạo danh mục"
  CreateCategory(){
    if (this.category.categoryID == "" || this.category.categoryName == ""){
      alert("Lỗi! Phải nhập những trường có dấu *");
    }
    else{
      this.http.post('https://localhost:44343/api/Category/Create_Category', this.category)
      .subscribe(
        result => {
                    var res:any = result;
                    if (res != null) {
                      this.categorys = res;
                      alert("Đã thêm danh mục mới!");
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
  //#endregion "Tạo danh mục"


  //#region "Xóa danh mục"
  DeleteCategory(categoryID){
    var check = confirm("Bạn có muốn xóa nhãn hiệu này ?")
    if (check == true) {
      this.http.delete('https://localhost:44343/api/Category/Delete_Category/' + categoryID)
      .subscribe(
        result => {
                    var res: any = result;
                    if (res != null){
                      this.categorys = res;
                      alert("Đã xóa danh mục!");
                      this.SearchCategory();
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
  //#endregion "Xóa danh mục"


  //#region "Sửa danh mục"
  UpdateCategory(){
    this.http.put('https://localhost:44343/api/Category/Update_Category/' + this.category.categoryID, this.category)
    .subscribe(
      result => {
                  var res:any = result;
                  if (res != null) {
                    this.categorys = res;
                    alert("Đã sửa danh mục!");
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
  //#endregion "Sửa danh mục"


  //#region "Phân trang"
  goNext() {
    if (this.categorys.page < this.categorys.totalPage) {
      this.page = parseInt("" + this.page) + 1; 
      this.SearchCategory();
    }
    else {
      alert("Bạn đang ở trang cuối !!!")
    }
  }


  goPrevious() {
    if (this.categorys.page > 1) {
      this.page = parseInt("" + this.page) - 1; 
      this.SearchCategory();  
    }
    else {
      alert("Bạn đang ở trang đầu !!!")
    }
  }
  //#endregion "Phân trang"


  //#region "Các hàm của Modal"
  specificInformation(index) {
    this.category = this.categorys.data[index];
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
        this.category = this.categorys.data[index];
    }
    else {
        this.category = {
          categoryID: "",
          categoryName: "",
          createDay: this.nowDate.toJSON(),
          description: "",
          note: ""
        }
    }
  }
  //#endregion "Các hàm của Modal"
}