import re
with open("test.txt", "r") as f:
        test_str = f.read()
test_str = re.sub(r"[^\w\s]", "",test_str)
print(test_str)
regex = re.compile(r"(\b\w*(?:[йцкнгшщзхъфвпрлджчсмтьб]+|(?=\b))[уеёаоэыяию]"
                   r"[уеёаоэыяию](?:[йцкнгшщзхъфвпрлджчсмтьб]+|(?=\b))\w*\b)"
                   r"(?= \b(?:[уеёаоэыяию]*[йцкнгшщзхъфвпрлджчсмтьб][уеёаоэыяию]*"
                   r"[йцкнгшщзхъфвпрлджчсмтьб]?[уеёаоэыяию]*"
                   r"[йцкнгшщзхъфвпрлджчсмтьб]?[уеёаоэыяию]*"
                   r"|[уеёаоэыяию]+)\b)",re.MULTILINE | re.IGNORECASE)

matches = regex.findall(test_str)
print(*matches)