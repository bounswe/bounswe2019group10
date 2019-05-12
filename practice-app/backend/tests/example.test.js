const exampleTest = require('./exampleTest');

test('string returning working', async() => {
    const testResult = await exampleTest();
    expect(testResult).toMatch('working');
});