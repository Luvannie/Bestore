import { Customer } from './model/customer';

describe('Customer', () => {
  it('should create an instance', () => {
    expect(new Customer()).toBeTruthy();
  });
});
