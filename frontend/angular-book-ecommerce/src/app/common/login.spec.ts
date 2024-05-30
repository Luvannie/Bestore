import { LoginDTO } from './loginDTO';

describe('Login', () => {
  it('should create an instance', () => {
    expect(new LoginDTO({})).toBeTruthy();
  });
});
