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