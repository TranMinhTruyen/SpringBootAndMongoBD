import { HttpClient } from '@angular/common/http';
import { Component, Inject } from '@angular/core';

@Component({
  selector: 'app-brand',
  templateUrl: './brand.component.html',
  styleUrls: ['./brand.component.css']
})
export class BrandComponent {
  constructor(private http: HttpClient, @Inject('BASE_URL') baseUrl: string) {
    this.SearchBrand();
  }
  //#region "Khai báo các biến"
  checkloading: Boolean = false;
  size: number = 8;
  page: number = 1;
  keyword: String = "";
  brands: any = {
    data: [],
    totalRecord: 0,
    page: 0,
    size: this.size,
    totalPage: 0,
  }
  //#endregion "Khai báo các biến"

  //#region "Tìm thương hiệu"
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
  //#endregion "Tìm thương hiệu"


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
  setDefaultPic(event) {
    event.target.src = 'assets/placeholder.png';
  }
}
//#region "JavaSrcipt"
//#endregion "JavaSrcipt"