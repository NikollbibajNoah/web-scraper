from scraper import scrape

def main():
    jobs = scrape()

    print(f'All jobs in {len(jobs)} were scraped')

if __name__ == "__main__":
    main()