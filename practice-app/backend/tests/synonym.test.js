const synonymTest = require('./synonymTest');

test('string returning working', async() => {
  const testResult = await synonymTest();
  expect(testResult).toMatch('working');
});
