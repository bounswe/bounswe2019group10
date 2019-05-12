const rhymeTest = require('./rhymeTest');

test('string returning working', async () => {
    const testResult = await rhymeTest();
    expect(testResult).toMatch('working');
});
