/* Remove the sensitive categories of Genetic and Addiction, which don't have any value sets */
DELETE FROM pcm.value_set_category
where code = 'GDIS' or code = 'ADD';