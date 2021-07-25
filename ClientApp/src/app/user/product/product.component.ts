import { HttpClient } from '@angular/common/http';
import { Component, Inject } from '@angular/core';
import { ActivatedRoute } from '@angular/router'; 
import { CartComponent } from '../cart/cart.component';

declare var $: any;
@Component({
  selector: 'app-product',
  templateUrl: './product.component.html',
  styleUrls: ['./product.component.css'],
})
export class ProductComponent {
    constructor(private http: HttpClient, @Inject('BASE_URL') baseUrl: string, private route: ActivatedRoute) {
    }


    ngOnInit() { 
        this.route.queryParams.subscribe( 
        params => { 
            this.id =  params['productID']; 
        });
        this.SearchProduct(); 
    } 


    //#region "Khai báo các biến"
    id: String = "";
    nowDate = new Date();
    size: number = 1;
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
        this.http.get<any>('https://localhost:44343/api/Product/Search_Product/' + this.size + ',' + this.page + '?keyword=' + this.id)
        .subscribe(
            result => {
                        var res: any = result;
                        if (res != null) {
                            this.products = res;
                        }
                    },
            error =>  {
                        alert("Server error !");
                    }
        );
        }
    }
    //#endregion "Tìm sản phẩm"


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
    setDefaultPic(event) {
        event.target.src = 'assets/placeholder.png';
    }
}