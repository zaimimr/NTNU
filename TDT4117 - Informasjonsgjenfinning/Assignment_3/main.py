import random
import codecs
import string
import re
from nltk.stem.porter import PorterStemmer
from nltk.probability import FreqDist
import gensim

# 1.0
random.seed(123)

# 1.1
f = codecs.open("pg3300.txt", "r", "utf-8")
# 1.2
paragraphs = re.split(r"(?:\r?\n){2,}", f.read())

# 1.3
paragraphs_without_Gutenberg = list(filter(lambda paragraph: "Gutenberg" not in paragraph, paragraphs))

# 1.5
special_characters_pattern = "[" + string.punctuation + "\n\r\t" + "]"
# paragraphs_without_special_characters = list(map(lambda paragraph: re.sub(special_characters_pattern, "", paragraph).lower(), paragraphs_without_Gutenberg))
# 1.4
# tokenized_paragraphs = list(map(lambda paragraph: paragraph.split(), paragraphs_without_special_characters))
# 1.6
stemmer = PorterStemmer()
# stemmed_paragraphs = list(map(lambda paragraph: list(map(stemmer.stem, paragraph)), tokenized_paragraphs))

# 1.4, 1.5 and 1.6 in one function
def preprocessing(text):
    processed_text = list(map(lambda text: re.sub(special_characters_pattern, "", text).lower(), text.split()))
    return list(map(stemmer.stem, processed_text))
stemmed_paragraphs = list(map(preprocessing, paragraphs_without_Gutenberg))

# 2.1
dictionary = gensim.corpora.Dictionary(stemmed_paragraphs)

# 2.1
stop_words = "a,able,about,across,after,all,almost,also,am,among,an,and,any,are,as,at,be,because,been,but,by,can,cannot,could,dear,did,do,does,either,else,ever,every,for,from,get,got,had,has,have,he,her,hers,him,his,how,however,i,if,in,into,is,it,its,just,least,let,like,likely,may,me,might,most,must,my,neither,no,nor,not,of,off,often,on,only,or,other,our,own,rather,said,say,says,she,should,since,so,some,than,that,the,their,them,then,there,these,they,this,tis,to,too,twas,us,wants,was,we,were,what,when,where,which,while,who,whom,why,will,with,would,yet,you,your"
stop_ids = [dictionary.token2id[word] for word in stop_words.split(",") if word in dictionary.token2id]
dictionary.filter_tokens(stop_ids)

# 2.2
bag_of_words = list(map(dictionary.doc2bow, stemmed_paragraphs))

# 3.1
tfidf_model = gensim.models.TfidfModel(bag_of_words)
# 3.2
tfidf_corpus = tfidf_model[bag_of_words]
# 3.3
tfidf_index  = gensim.similarities.MatrixSimilarity(tfidf_corpus)

# 3.4
lsi_model = gensim.models.LsiModel(tfidf_corpus, id2word=dictionary, num_topics=100)
lsi_corpus = lsi_model[bag_of_words]
lsi_index  = gensim.similarities.MatrixSimilarity(lsi_corpus)
# 3.5
lsi_result = lsi_model.show_topics()
  
# 4.1
query_text = "What is the function of money?"
# query_text = "How taxes influence Economics?"

query = preprocessing(query_text)
# 4.1
query = dictionary.doc2bow(query)

# 4.2
tfidf_query = tfidf_model[query]
tfidf_query_formated = list(map(lambda x: (dictionary[x[0]], str(round(x[1], 2))), tfidf_query))

# 4.3
query_index = enumerate(tfidf_index[tfidf_query])
query_index_sorted = sorted(query_index, key=lambda x: -x[1])[:3]

# 4.4
lsi_query = lsi_model[tfidf_query]
sorted_lsi_query = sorted(lsi_query, key=lambda kv: -abs(kv[1]))[:3]
doc2similarity = enumerate(lsi_index[lsi_query])
sorted_doc2similarity = sorted(doc2similarity, key=lambda kv: -kv[1])[:3]


print(tfidf_query_formated)

for paragraph in query_index_sorted:
    print(f'[paragraph {paragraph[0]}]')
    print(f'{paragraphs_without_Gutenberg[paragraph[0]]}')
    print()

for topic, _ in sorted_lsi_query:
    print(f'[topic {topic}]')
    print(f'{lsi_model.show_topics()[topic][1]}')