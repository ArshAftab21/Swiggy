import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { HttpInterceptor,
  HttpRequest,
  HttpHandler,
  HttpEvent
 } from '@angular/common/http';
import { AuthserviceService } from '../authentication/authservice.service';
@Injectable({
  providedIn: 'root'
})
export class TokenInterceptorService implements HttpInterceptor{

  constructor(private authService: AuthserviceService) { }
 intercept(request: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {

    console.log('In token interceptor');
    request = request.clone({
      setHeaders: {
        Authorization: `Bearer ${this.authService.getToken()}`
      }
    });
    return next.handle(request);
  }
}


