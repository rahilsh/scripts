import requests

print('Enter GitHub token:')
token = input()

headers = dict()

headers['Authorization'] = 'token ' + token
headers['Accept'] = 'application/vnd.github.v3+json'
for repo in open("../data/repos_to_fork.csv"):
    csv_row = repo.split()
    repo_name = csv_row[0]
    print(repo_name)
    url = url = 'https://api.github.com/repos/' + repo_name + '/forks'
    response = requests.post(url=url, headers=headers).json()
    print(response)
