// Angular has feature of defining type of variables. These models are shape of the response object
// For each endpoint we will call in our app we have to define corressponding model to be able to refer 
// fields of response object inside our components
// word: string means there is a variable of type string
// synonyms: [string] means synonyms is a array of strings

// definitions: [
//     {
//         definition: string;
//         partOfSpeech: string;
//     }
// ] means definitions is array of objects which have two strings


// You have to modify this file

export interface DefinitionModel{
        word: string;
        definitions: [
            {
                definition: string;
                partOfSpeech: string;
            }
        ];  
};

export interface SynonymModel{
    word: string;
    synonyms: [string];
};

export interface AntonymModel{
        word: string,
        antonyms: [string]
};

export interface SimilarModel{
    word: string,
    similars: [string]
};

export interface ExampleModel{
    word: string,
    examples: [string]
};
