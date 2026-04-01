from scraper import scrape

def main():
    jobs = scrape()

    print(f'Alle Jobs {len(jobs)} wurden gesammelt')

if __name__ == "__main__":
    main()