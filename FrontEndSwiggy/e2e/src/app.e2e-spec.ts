import { AppPage } from './app.po';
import {browser, by, element} from 'protractor';
import { protractor } from 'protractor/built/ptor';


fdescribe('workspace-project App', () => {
  let page: AppPage;

  beforeEach(() => {
    page = new AppPage();
  });

  it('should display welcome message', () => {
    page.navigateTo();
    expect(page.getTitleText()).toEqual('Welcome to Swiggy!');
  });

  it('should be redirected to home on opening the application', () => {
    expect(browser.getCurrentUrl()).toContain('/home');
  });

  it('should be redirected to /login route', () => {
    browser.element(by.css('#loginNav')).click();

	browser.driver.sleep(2000);

    expect(browser.getCurrentUrl()).toContain('/register');
  });

  it('should be redirected to /register route', () => {
    browser.element(by.css('#register')).click();

	browser.driver.sleep(2000);

    expect(browser.getCurrentUrl()).toContain('/register');
  });

  it('should be able to register user',()=> {
    browser.element(by.id('username')).sendKeys('Super User');
    browser.element(by.id('password')).sendKeys('SuperlastUser@123');
    browser.element(by.id('phone')).sendKeys('8756895142');
    browser.element(by.id('email')).sendKeys('Super@gmail.com');

    browser.element(by.css('#registerUser')).click();
    browser.driver.sleep(2000);

    expect(browser.getCurrentUrl()).toContain('/login');


  });

  it('should be able to login user and navigate to tournaments', () => {
    browser.element(by.id('uname')).sendKeys('Super User12');
    browser.element(by.id('password')).sendKeys('Super Userpass');

    browser.element(by.css('#login')).click();

    expect(browser.getCurrentUrl()).toContain('/fooditems');

  });

  
  it('should be able to addToCart',()=> {
    browser.element(by.id('#2')).sendKeys(2);
    browser.element(by.css('#Add_2')).click();
    browser.driver.sleep(2000);

    expect(browser.getCurrentUrl()).toContain('/fooditems');


  });

  it('should be redirected to /cart route', () => {
    browser.element(by.css('#cartNav')).click();

	browser.driver.sleep(2000);

    expect(browser.getCurrentUrl()).toContain('/cart');
  });

  it('should be able to place Order', () => {
    browser.element(by.css('#placeOrder')).click();

	browser.driver.sleep(2000);

    expect(browser.getCurrentUrl()).toContain('/orderplaced');
  });

  it('should be able to logout', () => {
    browser.element(by.css('#logoutNav')).click();

	browser.driver.sleep(2000);

    expect(browser.getCurrentUrl()).toContain('/home');
  });






});
