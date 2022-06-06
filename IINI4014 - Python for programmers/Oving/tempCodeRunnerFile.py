import re

def count_words(text):
	skips = [".", ", ", ":", ";", "'", '"', '\n', '?', '!']
	for ch in skips:
		text = text.replace(ch, "")
	word_counts = {}
	word_counts['.nr_count'] = 0
	for word in re.split(" ", text):
		word_counts['.nr_count'] += 1
		if word in word_counts:
			word_counts[word] += 1
		else:
			word_counts[word]= 1
	return word_counts

def avg_words_in_sentence(text):
	text.replace('! ', '. ')
	text.replace('? ', '. ')
	sentences = text.split('.')
	word_count = count_words(text)
	return word_count['.nr_count']/len(sentences)

def sentences_paragraph(text):
	paragraphs = text.split('\n\n')
	par = []
	i = 0
	for paragraph in paragraphs:
		par.append(len(paragraph.split('. ')))
	sum = 0
	for p in par:
		sum += p
	return sum/len(par)

def easy_word(text):
		word_list = count_words(text)
	tot_words = word_list['.nr_count']
	del word_list['.nr_count']
	easy = 0
	for word in word_list:
		if word_list[word] > 50:
			easy+=1
	print(easy)
	return easy/tot_words

def hard_word(text):
	word_list = count_words(text)
	tot_words = word_list['.nr_count']
	del word_list['.nr_count']
	hard = 0
	for word in word_list:
		if word_list[word] < 5:
			hard+=1
	print(hard)
	return hard/tot_words

def getWords(file):
	text = ''
	with open(file,'r') as f:
		for line in f:
			text+=line
	return text


data_file = "./books/folder_00/pg5200.txt"
a = getWords(data_file)
print(count_words(a))
print(avg_words_in_sentence(a))
print(sentences_paragraph(a))
print(str(easy_word(a)*100) + '%')
print(str(hard_word(a)*100) + '%')
