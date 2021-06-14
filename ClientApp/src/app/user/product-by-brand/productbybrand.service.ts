import { Injectable } from '@angular/core';
import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { catchError } from 'rxjs/operators';
import { throwError } from 'rxjs';

const baseUrl = 'https://localhost:44343/api/Product/'

@Injectable({
    providedIn: 'root'
})

export class ProductByBrandService{
    constructor(private http: HttpClient){}

    getByKeyword(size, page, keyword){
        if (page < 0) {
            alert("Lỗi! Trang không hợp lệ!")
        }   
        else {
            return this.http.get(baseUrl+'/Search_Product/'+size+','+page+'?keyword='+keyword)
            .pipe(catchError(this.errorHandler))
        }
    }

    UpdateProductAmount(productID, amount){
        return this.http.put(baseUrl + 'Update_Unit_In_Stock/'+ productID, amount)
        .pipe(catchError(this.errorHandler))
    }

    errorHandler(error: HttpErrorResponse){
        return throwError(error.message || "server error.")
    }
}