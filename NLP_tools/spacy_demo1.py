# Karen Mazidi 10/4/2018
# demo some basic functionality in SpaCy
# if you get an error message about en_... not found
# try this at the console: python -m spacy download en
# for the medium model:  python -m spacy download en_core_web_md

# learn more at: https://spacy.io/usage/spacy-101

def demo(text):
    """
    Demo some basic SpaCy functionality
    :param text: raw text
    """

    import spacy

    # set up nlp object which is a language model of english
    #   and apply nlp to the text, creating a doc
    #   a doc holds the original text and all annotations
    #   the nlp() object divides the text into sentences,
    #       creates a dependency parse,
    #       tokenizes the text, annotates tokens
    nlp = spacy.load('en_core_web_sm')
    doc = nlp(text)

    # extract the sentences
    #   find and display the ROOT and subject for each sentence
    #   by traversing the dependency parse
    sentences = list(doc.sents)
    for sent in sentences:
        print('\n', sent)
        sent_doc = nlp(sent.text)
        # each token has a dependency label "dep_"
        # assume the first one is the ROOT, there should only be one
        root = [token for token in sent_doc if token.dep_ == 'ROOT'][0]
        print('ROOT=', root)

        # find subject
        subject = None
        for token in root.lefts:
            if token.dep_ in ['nsubj', 'nsubjpass']:
                subject = token
                if subject.dep_ == 'nsubjpass':
                    passive = True
                else:
                    passive = False
                break
        if not subject:
            print("Error: did not find subject")
            continue
        print('subject=', subject, 'passive=', passive)


    # grab first sentence
    sample = nlp(sentences[0].text)
    # print attributes of each token
    print('\n\nSentence 1 tokens:')
    for token in sample:
        print(token.text, token.lemma_, token.pos_, token.is_stop)

    # check for named entities
    print("\n\n Named Entities:")
    for ent in doc.ents:
        print(ent.text, ent.label_)

    # get noun chunks
    print("\n\nNoun Chunks (the first 5):")
    noun_chunks = list(doc.noun_chunks)
    # print first few chunks
    print(noun_chunks[:])


    # experiment with vectors
    # 3 words from the text
    # the medium model will get better results than the small one
    nlp_large = spacy.load('en_core_web_md')
    text_tokens = nlp_large(u'heart thoracic lung pinecone')
    print("\n\nVector similarities:")
    for token1 in text_tokens:
        for token2 in text_tokens:
            print(token1.text, token2.text, token1.similarity(token2))
    # heart and thoracic: 0.32
    # heart and lung 0.44
    # heart and pinecone 0.24
    # thoracic and lung 0.5
    # thoracic and pinecone -0.04
    # lung and pinecone 0.05


# run the demo
if __name__ == "__main__":
    # read in some sample text
    text = open('anat.txt').read()
    demo(text)