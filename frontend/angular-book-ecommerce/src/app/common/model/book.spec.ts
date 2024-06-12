import { Book } from './model/book';

describe('Book', () => {
  it('should create an instance', () => {
    expect(new Book()).toBeTruthy();
  });
});
