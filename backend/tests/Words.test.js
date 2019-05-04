

const words = require('../api/Words');



console.log(words());

test('string returning hello', () => {
    expect(words()).toMatch('hello there jest');
});
