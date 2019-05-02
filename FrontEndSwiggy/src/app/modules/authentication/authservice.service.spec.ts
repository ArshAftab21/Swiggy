
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { AuthserviceService } from './authservice.service';
import { HttpClientModule } from '@angular/common/http';
import { TestBed, inject, fakeAsync } from '@angular/core/testing';

const testConfig = {
  userData: {
    id: 'testId',
    userName: 'testName',
    password: 'testPass',
    phoneNumber: 'testPhone',
    email: 'testEmail'
  },
  loginUser: {
    positive: {
      userName: 'testUser',
      password: 'testPass'
    }
  }

}


fdescribe('AuthserviceService', () => {
  let authService: AuthserviceService;

  beforeEach(() =>{
     TestBed.configureTestingModule({
      imports: [HttpClientModule,HttpClientTestingModule],
      providers: [AuthserviceService]

     });
     authService=TestBed.get(AuthserviceService);
    });

  it('should be created authenticate service', () => {
    inject([AuthserviceService], (service: AuthserviceService) => {
      expect(service).toBeTruthy();
  
  });
});
it('should register user data', fakeAsync(() => {
  let data = testConfig.userData;
  inject([AuthserviceService, HttpTestingController], (backend: HttpTestingController) => {
    const mockReq = backend.expectOne(authService.authServiceEndpoint);
    expect(mockReq.request.url).toEqual(authService.authServiceEndpoint, 'request url should match with json server api url');
    expect(mockReq.request.method).toBe('POST', 'Should handle requested method type');
    mockReq.flush(data);
    backend.verify();
  });
  authService.registerUser(data).subscribe((res: any) => {
    expect(res).toBeDefined();
    expect(res._body).toBe(data, 'data should be same');
  });
}));

it('should login user', fakeAsync(() => {
  let userData = testConfig.loginUser.positive;
  inject([AuthserviceService, HttpTestingController], (backend: HttpTestingController) => {
    const mockReq = backend.expectOne(authService.authServiceEndpoint);
    expect(mockReq.request.url).toEqual(authService.authServiceEndpoint, 'request url should match with json server api url');
    expect(mockReq.request.method).toBe('POST', 'Should handle requested method type');
    mockReq.flush(userData);
    backend.verify();
  });
  authService.loginUser(userData).subscribe((res: any) => {
    expect(res).toBeDefined();
    expect(res._body).toBe(userData, 'data should be same');
  });
}));
});

