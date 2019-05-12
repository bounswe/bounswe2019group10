const antonymTest = require('./antonymTest');

test('string returning working', async() => {
    const testResult = await antonymTest();
    expect(testResult).toMatch('working');
});